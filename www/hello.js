/*global cordova, module*/

module.exports = {
    greet: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Hello", "greet", [name]);
    },
    chatDemo: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ALChatManager", "chatDemo", [name]);
    }
};
