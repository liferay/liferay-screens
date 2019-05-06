function callOffice() {
  window.plugins.CallNumber.callNumber(function (result) {
    console.log('Success:' + result);
  }, function (result) {
    console.log('Error:' + result);
  }, '900900900', true);
}

window.callOffice = callOffice;