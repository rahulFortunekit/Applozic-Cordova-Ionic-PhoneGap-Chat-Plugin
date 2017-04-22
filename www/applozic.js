/*global cordova, module*/

module.exports = {
    login: function(alUser, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "login", [JSON.stringify(alUser)]);
    },
    launchChat: function(userId, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "launchChat", [userId]);
    },
    launchChatWithUserId: function(userId, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "launchChatWithUserId", [userId]);
    },
    launchChatWithGroupId: function(groupId, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "launchChatWithGroupId", [groupId]);
    },
    launchChatWithClientGroupId: function(clientGroupid, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "launchChatWithClientGroupId", [clientGroupid]);
    },
    logout: function(successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "logout", []);
    }
};
