function modifyItem() {

    var urlIcons = {
        documentPdf: '/documents/33300/39232/pdf.png/8c743026-cc37-07c3-1681-d7c9f4083b59',
        images: '/documents/33300/39232/image.png/c9b5b54f-3996-756b-622b-fc02c56e0475',
        music: '/documents/33300/39232/music.png/f5ea120d-cbee-2a7c-b1e5-b249f84796c5',
        others: '/documents/33300/39232/file.png/c48b31b9-e94a-866a-19d5-f8debacc9ec8'
    };

    var icons = {
        pdf: urlIcons.documentPdf,
        png: urlIcons.images,
        jpg: urlIcons.images,
        jpge: urlIcons.images,
        mp3: urlIcons.music,
        def: urlIcons.others
    };

    var documentItems = document.querySelectorAll('.item-doc');

    for (var i = 0; i < documentItems.length; i++) {
        addClick(documentItems[i]);

        var ext = documentItems[i].getAttribute('data-extension');
        documentItems[i].getElementsByTagName('img')[0].src = icons[ext] || icons.def;

        if (i != documentItems.length - 1) {
            addSeparator(documentItems[i]);
        }
    }
}

function addClick(documentItems) {
    var dataId = documentItems.getAttribute('data-id');
    documentItems.addEventListener('click', function(event) {
        window.Screens.postMessage('doc-item', dataId);
        console.log("Document Id: " + dataId);
    });
}

function addSeparator(documentItems) {
    var div = document.createElement('div');
    div.className = 'separator';
    documentItems.appendChild(div);
}

modifyItem();