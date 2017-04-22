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
    openChat: function(userId, successCallback, errorCallback) { //Todo: remove it
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "openChat", [userId]);
    },

    init: function(alUser, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "registerALUser", [JSON.stringify(alUser)]);
    },
    launchChatWtithUserId: function(userId, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "openChat", [userId]);
    },
    launchChatWtithGroupId: function(groupId, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "openChat", [groupId]);
    },
    launchChatWtithClientGroupId: function(clientGroupid, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "openChat", [clientGroupid]);
    },
    logout: function(successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "logout", []);
    }
};
