function modifyItem() {

    var itemClassName = 'com.liferay.document.library.kernel.model.DLFileEntry';

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

    var lastChangeItems = document.querySelectorAll('.item-last-changes');

    for (var i = 0; i < lastChangeItems.length; i++) {

        var item = lastChangeItems[i];

        addClick(item);

        if (item.getAttribute('data-class-name') == itemClassName) {
            var ext = item.getAttribute('data-extension');
            item.getElementsByTagName('img')[0].src = icons[ext] || icons.def;
        }

        if (i != lastChangeItems.length - 1) {
            addSeparator(item);
        }
    }
}

function addClick(lastChangeItem) {
    var dataId = lastChangeItem.getAttribute('data-id');
    var dataExtension = lastChangeItem.getAttribute('data-extension');
    lastChangeItem.addEventListener('click', function(event) {
        window.Screens.postMessage('last-changes-item', dataId + '|' + dataExtension);
    });
}


function addSeparator(lastChangeItem) {
    var div = document.createElement('div');
    div.className = 'separator';
    lastChangeItem.appendChild(div);
}

modifyItem();