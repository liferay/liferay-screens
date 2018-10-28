function modifyItem() {
  var blogItems = document.querySelectorAll('.item-blog');

  for (var i = 0; i < blogItems.length; i++) {
    blogItems[i].style = null;
    addClick(blogItems[i]);
    if (i !== blogItems.length - 1) {
      addSeparator(blogItems[i]);
    }
  }
}

function addClick(blogItem) {
  var dataId = blogItem.getAttribute('data-id');
  blogItem.addEventListener('click', function (event) {
    window.Screens.postMessage('blog-item', dataId);
  });
}

function addSeparator(blogItem) {
  var div = document.createElement('div');
  div.className = 'separator';
  blogItem.appendChild(div);
}

modifyItem();