function modifyItem() {

    var ratings = document.querySelectorAll(".rating-label");
    ratings[0].innerHTML = "";

    /*
    cleanRatingText(ratings[1]);
    var etqA = document.getElementsByTagName("a");

    for(var i = 0; i < etqA.length; i++) {
        etqA[0].addEventListener('click', function(event) {
            cleanRatingText(ratings[1]);
        });
    }
    */

    var contentRatingScore = document.getElementById("zyfa_column1_0_ratingScoreContent");
    contentRatingScore.appendChild(ratings[1]);

}

modifyItem();