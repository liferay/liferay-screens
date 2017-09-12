function buildMap(){
    var newBody = document.createElement('body');
    var map = document.getElementsByClassName('map-container')[0];
    var legend = document.getElementsByClassName('legend')[0];
    
    map.removeChild(legend);
    map.appendChild(legend);
    
    document.body = newBody;
    document.body.appendChild(map);
}

buildMap();
