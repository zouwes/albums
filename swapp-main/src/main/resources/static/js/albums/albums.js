document.getElementById('artistsInput').addEventListener('keypress', function (event) {
    if (event.key === 'Enter') {
        searchAlbums();
    }
});

function buildSearchQuery() {
    const artists = document.getElementById('artistsInput').value.trim();
    const minDuration = document.getElementById('minDurationInput').value.trim();
    const maxDuration = document.getElementById('maxDurationInput').value.trim();

    let queryParts = [];

    if (artists) {
        queryParts.push(`artists=${encodeURIComponent(artists)}`);
    }
    if (minDuration) {
        queryParts.push(`minDuration=${encodeURIComponent(minDuration)}`);
    }
    if (maxDuration) {
        queryParts.push(`maxDuration=${encodeURIComponent(maxDuration)}`);
    }

    return queryParts.length > 0 ? `?${queryParts.join('&')}` : '';
}

function searchAlbums() {
    const query = buildSearchQuery();
    window.location.href = '/albums' + query;
}

document.addEventListener('DOMContentLoaded', function () {
    const query = buildSearchQuery();
    history.pushState({}, '', '/albums' + query);

    const albumsList = document.getElementById('albumsList');

    albumsList.addEventListener('click', function (event) {
        const target = event.target;

        if (target.classList.contains('delete-button')) {
            const albumId = target.getAttribute('data-id');
            fetch('/albums/' + albumId, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        console.log('Album deleted successfully');
                        window.location.reload();
                    } else {
                        console.error('Failed to delete album');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    });
});
