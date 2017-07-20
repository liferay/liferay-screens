function modifyItem() {
    
    var itemClassName = 'com.liferay.document.library.kernel.model.DLFileEntry';
    
    var urlIcons = {
        pdf: "/documents/20143/56606/pdf.png/47038737-967d-578e-06ca-3b690d5c87b2?t=1499173758257",
        img: "/documents/20143/56606/image.png/41daf5f2-e3b9-39fc-2db0-96d02963a88e?t=1499173747235",
        mus: "/documents/20143/56606/music.png/3ebeeaa2-4164-d508-3f5f-b8842bef32c0?t=1499173711480",
        def: "/documents/20143/56606/file.png/6939decf-8c2d-c195-81d4-b225403dd2f5?t=1499174224790"
    };
    
    var icons = {
        pdf: urlIcons.pdf,
        png: urlIcons.img,
        jpg: urlIcons.img,
        jpeg: urlIcons.img,
        mp3: urlIcons.mus,
        def: urlIcons.def
    };
    
    
    var lastChangeItems = document.querySelectorAll(".item-last-changes");
    
    for (var i = 0; i < lastChangeItems.length; i++) {
        
        addClick(lastChangeItems[i]);
        
        if (lastChangeItems[i].getAttribute('data-class-name') == itemClassName){
            var ext = lastChangeItems[i].getAttribute('data-extension');
            lastChangeItems[i].getElementsByTagName('img')[0].src = icons[ext] || icons.def;
        }
        
        if (i != lastChangeItems.length - 1) {
            addSeparator(lastChangeItems[i]);
        }
    }
}

function addClick(lastChangeItem) {
    var dataId = lastChangeItem.getAttribute('data-id');
    var dataExtension = lastChangeItem.getAttribute('data-extension');
    lastChangeItem.addEventListener('click', function(event) {
        window.Screens.postMessage("last-changes-item", dataId + "|" + dataExtension);
    });
}


function addSeparator(lastChangeItem) {
    var div = document.createElement('div');
    div.className = "separator";
    lastChangeItem.appendChild(div);
}

modifyItem();
