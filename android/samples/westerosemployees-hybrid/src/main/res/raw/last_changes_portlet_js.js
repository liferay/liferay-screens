function modifyItem() {
  var itemClassName = 'com.liferay.document.library.kernel.model.DLFileEntry';

  var urlIcons = {
    pdf: '/documents/33300/39232/pdf.png/8c743026-cc37-07c3-1681-d7c9f4083b59',
    img: '/documents/33300/39232/image.png/c9b5b54f-3996-756b-622b-fc02c56e0475',
    mus: '/documents/33300/39232/music.png/f5ea120d-cbee-2a7c-b1e5-b249f84796c5',
    def: '/documents/33300/39232/file.png/c48b31b9-e94a-866a-19d5-f8debacc9ec8'
  };

  var icons = {
    pdf: urlIcons.pdf, png: urlIcons.img, jpg: urlIcons.img, jpeg: urlIcons.img, mp3: urlIcons.mus, def: urlIcons.def
  };

  var lastChangeItems = document.querySelectorAll('.item-last-changes');

  for (var i = 0; i < lastChangeItems.length; i++) {

    var lastChangeItem = lastChangeItems[i];
    addClick(lastChangeItem);

    if (lastChangeItem.getAttribute('data-class-name') === itemClassName) {
      var ext = lastChangeItem.getAttribute('data-extension');
      lastChangeItem.getElementsByTagName('img')[0].src = icons[ext] || icons.def;
    }

    if (i !== lastChangeItems.length - 1) {
      addSeparator(lastChangeItem);
    }
  }
}

function addClick(lastChangeItem) {
  var dataId = lastChangeItem.getAttribute('data-id');
  lastChangeItem.addEventListener('click', function (event) {
    window.Screens.postMessage('last-changes-item', dataId);
  });
}

function addSeparator(lastChangeItem) {
  var div = document.createElement('div');
  div.className = 'separator';
  lastChangeItem.appendChild(div);
}

modifyItem();
