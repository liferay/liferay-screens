function ahora(){
    console.log('Me has ejecutaoooo!!');
}

function showLogin(){
    console.log('Llamando!!!!');
}

function gotoId(anchor){
    var anchor = anchor.slice(1, anchor.length)
    document.getElementById(anchor).scrollIntoView(true);
}

function buildMenu(){
    var menuList = document.getElementsByClassName('menu-list')[0];
    if (menuList != undefined) {
        var items = menuList.getElementsByTagName("li");
        var menuLine = '';
        for (var i = 0; i < items.length; i++) {
            item = items[i].innerText + ',' + items[i].firstElementChild.getAttribute('href');
            menuLine += i != 0 ? '|' + item : item;
        }
        window.Screens.postMessage('menu', menuLine);
    }
}

buildMenu();