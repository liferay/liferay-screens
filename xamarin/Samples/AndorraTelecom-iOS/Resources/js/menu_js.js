
var divCallMeBack;

function buildMenu() {
    var newBody = document.createElement('body');
    var bannerTop = document.getElementsByClassName('big-banner cont-wrapper')[0];
    var icons = document.getElementsByClassName('item picture');
    var titles = document.getElementsByClassName('item title');
    var footer = document.getElementById('footer-links');
    var contentButtonMap = footer.getElementsByTagName('h3')[1].innerText;

    removeItemByClassName(bannerTop, 'flex-control-nav flex-control-paging', 0);
    removeItemByClassName(bannerTop, 'flex-direction-nav', 0);
    removeItemsByClassName(bannerTop, 'block-container');
    bannerTop.style.width = window.innerWidth + 'px';
    bannerTop.style.height = '175px';

    var buttonMap = createButtonMap(contentButtonMap);
    var buttonList = createButtons(icons, titles);
    divCallMeBack = document.getElementsByClassName('call-me-back')[0];

    document.body = newBody;
    document.body.appendChild(buttonMap);
    document.body.insertBefore(buttonList, buttonMap);
    document.body.insertBefore(bannerTop, buttonList);
}

function createButtonMap(text) {
    var containerButton = document.createElement('div');
    containerButton.className = 'coverage-container';

    var buttonMap = document.createElement('button');
    buttonMap.className = 'coverage-button';
    buttonMap.innerHTML = text;

    buttonMap.addEventListener('click', function(event) {
        window.Screens.postMessage('map', '');
    });

    containerButton.appendChild(buttonMap);
    return containerButton;
}

function removeItemByClassName(node, className, position) {
    var item = node.getElementsByClassName(className)[position];
    item.remove();
}

function removeItemsByClassName(node, className) {
    var items = node.getElementsByClassName(className);
    for (var i = items.length - 1; i >= 0; i--) {
        items[i].remove();
    }
}

function createButtons(icons, titles) {

    var buttonList = document.createElement('ul');

    for (var i = 0; i < icons.length; i++) {
        var button = createButton(icons[i], titles[i]);
        addClick(i, button)
        buttonList.appendChild(button);
    }

    return buttonList;
}

function createButton(icon, title) {
    var button = document.createElement('li');
    button.className = 'item-button';

    var cloneIcon = icon.cloneNode(true);
    var cloneTitle = title.cloneNode(true);
    button.appendChild(cloneIcon);
    button.appendChild(cloneTitle);

    return button;
}

function addClick(position, button) {
    button.addEventListener('click', function(event) {
        window.Screens.postMessage('click-button', '' + position);
    });
}

function getTextCallMeBack() {
    var headerCallMeBack = divCallMeBack.getElementsByTagName('h2')[0];
    window.Screens.postMessage('call-me-back', headerCallMeBack.innerText);
}

buildMenu();
getTextCallMeBack();