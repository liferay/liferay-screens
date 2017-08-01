function callOffice() {
    if (isSimulator()) {
        alert('This feature is only avaible in a real device.');
    } else {
        window.plugins.CallNumber.callNumber(null, null, '900900900', true);
    }
}

function isSimulator() {
    return device && device.model.match(/x86/);
}
