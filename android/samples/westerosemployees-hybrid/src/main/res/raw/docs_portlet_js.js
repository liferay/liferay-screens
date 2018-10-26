function modifyItem() {
  var urlIcons = {
    pdf: '/documents/33300/39232/pdf.png/8c743026-cc37-07c3-1681-d7c9f4083b59',
    img: '/documents/33300/39232/image.png/c9b5b54f-3996-756b-622b-fc02c56e0475',
    mus: '/documents/33300/39232/music.png/f5ea120d-cbee-2a7c-b1e5-b249f84796c5',
    def: '/documents/33300/39232/file.png/c48b31b9-e94a-866a-19d5-f8debacc9ec8'
  };

  var icons = {
    pdf: urlIcons.pdf, png: urlIcons.img, jpg: urlIcons.img, jpge: urlIcons.img, mp3: urlIcons.mus, def: urlIcons.def
  };

  var documentItems = document.querySelectorAll('.item-doc');

  for (var i = 0; i < documentItems.length; i++) {
    var item = documentItems[i];
    addClick(item);

    var ext = item.getAttribute('data-extension');
    item.getElementsByTagName('img')[0].src = icons[ext] || icons.def;

    if (i !== documentItems.length - 1) {
      addSeparator(item);
    }
  }
}

function addClick(item) {
  var dataId = item.getAttribute('data-id');
  item.addEventListener('click', function (event) {
    window.Screens.postMessage('doc-item', dataId);
  });
}

function addSeparator(item) {
  var div = document.createElement('div');
  div.className = 'separator';
  item.appendChild(div);
}

modifyItem();
