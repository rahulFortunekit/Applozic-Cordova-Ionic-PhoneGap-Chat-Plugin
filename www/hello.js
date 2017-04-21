/*global cordova, module*/

module.exports = {
    greet: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Hello", "greet", [name]);
    },
    chatDemo: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "chatDemo", [name]);
    },
    registerALUser: function(alUser, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "registerALUser", [JSON.stringify(alUser)]);
    },
    openChat: function(userId, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "openChat", [userId]);
    }
};
