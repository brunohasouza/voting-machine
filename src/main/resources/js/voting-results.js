class VotingResults {
    _url = 'http://localhost:8080/api';

    constructor() {
        this._partiesTable = document.querySelector('#political-parties');
        this._candidatesTable = document.querySelector('#candidates');
        this._blankEl = document.querySelector("#blank");
    }

    list(url) {
        return new Promise((resolve) => {
            fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => response.json())
            .then(json => resolve(json));
        });
    }

    drawPartiesTable(policitalParties) {
        this._partiesTable.innerHTML = policitalParties.map(politicalParty => `
            <tr>
                <td>${politicalParty.name}</td>
                <td>${politicalParty.abbreviation}</td>
                <td>${politicalParty.number}</td>
                <td>${politicalParty.votes}</td>
            </tr>
        `).join('');
    }

    drawCandidatesTable(candidates) {
        this._candidatesTable.innerHTML = candidates.map(candidate => {
            const resultClass = candidate.elected ? 'btn-outline-success' : 'btn-outline-secondary';
            const resultLabel = candidate.elected ? 'Sim' : 'Não';

            return `
                <tr class="${candidate.elected ? 'table-success' : ''}">
                    <td>${candidate.name}</td>
                    <td>${candidate.number}</td>
                    <td>${candidate.politicalParty.abbreviation}</td>
                    <td>${candidate.votes}</td>
                    <td>
                        <button class="btn btn-sm ${resultClass}" disabled>${resultLabel}</button>
                    </td>
                </tr>
            `
        }).join('');
    }

    drawBlankVotes(votes) {
        this._blankEl.innerHTML = votes;
    }

    getResults() {
        this.list(`${this._url}/results`)
            .then(data => {
                this.drawPartiesTable(data?.politicalParties || []);
                this.drawCandidatesTable(data?.candidates || []);
                this.drawBlankVotes(data?.blank?.votes || 0);
            })
    }

    init() {
        this.getResults();
    }
}