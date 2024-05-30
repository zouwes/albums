function addAlbum() {
    const title = document.getElementById('title').value;
    const duration = parseInt(document.getElementById('duration').value);
    const artists = document.getElementById('artists').value.split(',').map(artist => artist.trim());
    const producerName = document.getElementById('producerName').value;
    const producerNationality = document.getElementById('producerNationality').value;
    const year = parseInt(document.getElementById('year').value);

    const album = {
        title: title,
        duration: duration,
        artists: artists,
        details: {
            producer: {
                name: producerName,
                nationality: producerNationality
            },
            year: year
        }
    };

    fetch('/albums/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(album)
    }).then(response => {
        if (response.ok) {
            window.location.href = "/albums";
        } else {
            return response.text().then(text => {
                alert("Error: " + text);
            });
        }
    }).catch(error => {
        alert("Error: " + error);
    });
}

function addAlbumJson() {
    const albumJson = document.getElementById("albumInfo").value;

    var formData = {
        albumInfo: albumJson
    };

    var jsonData = JSON.stringify(formData);
    fetch("/albums/addjson", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: jsonData
    }).then(response => {
        if (response.ok) {
            window.location.href = "/albums";
        } else {
            return response.text().then(text => {
                alert("Error: " + text);
            });
        }
    }).catch(error => {
        alert("Error: " + error);
    });
}

document.addEventListener("DOMContentLoaded", function () {

    document.getElementById("addAlbumJson").addEventListener("submit", function (event) {
        event.preventDefault();
        addAlbumJson();
    });

    document.getElementById('addAlbumButton').addEventListener('click', function (event) {
        event.preventDefault();
        addAlbum();
    });
});
