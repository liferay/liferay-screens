function modifyItem() {
  var ratings = document.querySelectorAll('.rating-label');
  ratings[0].innerHTML = '';

  var contentRatingScore = document.getElementById('zyfa_column1_0_ratingScoreContent');
  contentRatingScore.appendChild(ratings[1]);
}

modifyItem();