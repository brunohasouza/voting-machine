class VotingMachine {
    _number = '';
    _blank = false;
    _url = 'http://localhost:8080/api';
    _valid = false;

    constructor() {
        this._numpad = document.querySelector('#numpad');
        this._inputs = document.querySelectorAll('.input-group .input-num');
        this._partyEl = document.querySelector('#political-party');
        this._candidateEl = document.querySelector('#candidate');
        this._messageEl = document.querySelector('#message');
    }

    searchPoliticalParty() {
        const xhr = new XMLHttpRequest();
        const url = `${this._url}/political-parties/${this._number}`;

        xhr.open('GET', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json')
        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4) {
                switch(xhr.status) {
                    case 200:
                        const data = JSON.parse(xhr.response);
                        this._partyEl.innerHTML = `${data.abbreviation} - ${data.name}`;
                        break;
                    case 204:
                        this._partyEl.innerHTML = `<span class="text-danger">Partido inválido</span>`;
                        break;
                    default:
                        alert("Erro ao recuperar partido. Tente novamente.");
                        break;
                }
            }
        }
        xhr.send();
    }

    searchCandidate() {
        const url = `${this._url}/candidates/${this._number}`;
        fetch(url, {
                method: 'GET',
                headers: { 'Content-Type': 'application/xml' }
            })
            .then(response => response.text())
            .then((candidate) => {
                if (!candidate) {
                    this._candidateEl.innerHTML = `<span class="text-danger">Candidate inválido</span>`;
                } else {
                    const parser = new DOMParser();
                    const doc = parser.parseFromString(candidate, "application/xml");
                    this._candidateEl.innerHTML = doc.documentElement.querySelector('name')?.innerHTML;
                    this._valid = true;
                }
            })
            .catch(() => {
                alert('Erro ao recuperar candidato. Tente novamente.');
                this.clear();
            })
    }

    registerVote() {
        return new Promise((resolve, reject) => {
            const xhr = new XMLHttpRequest();
            const url = `${this._url}/vote`;
            const xml = `
                <vote>
                    <candidate>${this._number}</candidate>
                    <isBlank>${this._blank}</isBlank>
                </vote>
            `;

            xhr.open('POST', url, true);
            xhr.setRequestHeader('Content-Type', 'application/xml');
            xhr.onreadystatechange = () => {
                if (xhr.readyState === 4) {
                    if (xhr.status !== 200) {
                        reject();
                        return;
                    }

                    resolve();
                }
            }

            xhr.send(xml);
        });
    }

    concatNumber(evt) {
        if (this._number.length === 4 || this._blank) {
            return;
        }

        this._number += evt.target.dataset.num;

        if (this._number.length === 2) {
            this.searchPoliticalParty();
        }

        if (this._number.length === 4) {
            this.searchCandidate();
        }

        this.updateScreen();
    }

    updateScreen() {
        const number = this._number.padStart(4, ' ');
        this._inputs.forEach((input, index) => input.value = number[index]);

        if (this._blank) {
            const blankContent = '<button class="btn btn-sm btn-outline-secondary" disabled>BRANCO</button>';
            this._candidateEl.innerHTML = blankContent;
            this._partyEl.innerHTML = blankContent;
        }
    }

    setupNumpad() {
        const buttons = [];

        for (let i = 0; i < 10; i++) {
            let number = i + 1 < 10 ? i + 1 : 0;
            buttons.push(`<button class="btn btn-dark btn-lg btn-num" data-num="${number}">${number}</button>`);
        }

        this._numpad.innerHTML = buttons.join('');
    }

    clear() {
        this._number = '';
        this._blank = false;
        this._inputs.forEach((input) => input.value = '');
        this._candidateEl.innerHTML = '';
        this._partyEl.innerHTML = '';
    }

    blank() {
        this._number = '';
        this._blank = true;
        this.updateScreen();
    }

    confirm() {
        if (!this._blank && !this._valid) {
            alert('Voto inválido! Para computar seu voto é necessário incluir um candidato válido ou votar em branco');
            return;
        }

        this.registerVote()
            .then(() => {
                this._messageEl.innerHTML = `
                    <div class="alert alert-success mb-0">
                        <h5 class="alert-heading">Voto registrado com sucesso!</h5>
                        <hr>
                        <p class="mb-0">Preparando urna para próximo eleitor...</p>
                    </div>
                `;

                setTimeout(() => {
                    this.clear();
                    this._messageEl.innerHTML = '';
                }, 2000);
            })
            .catch(() => alert('Não foi possível registrar seu voto ou você incluiu um candidato inválido. Tente novamente.'))
    }

    registerEvents() {
        const numBtns = this._numpad.querySelectorAll("button");
        numBtns.forEach(n => n.onclick = this.concatNumber.bind(this))

        const clearBtn = document.querySelector('.btn-clear');
        clearBtn.onclick = this.clear.bind(this);

        const blankBtn = document.querySelector('.btn-blank');
        blankBtn.onclick = this.blank.bind(this);

        const confirmBtn = document.querySelector('.btn-confirm');
        confirmBtn.onclick = this.confirm.bind(this)
    }

    init() {
        this.setupNumpad();
        this.registerEvents();
    }
}