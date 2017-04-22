/*global cordova, module*/

module.exports = {
    login: function(alUser, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "login", [JSON.stringify(alUser)]);
    },
    launchChatWtithUserId: function(userId, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "launchChatWithUserId", [userId]);
    },
    launchChatWtithGroupId: function(groupId, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "launchChatWithGroupId", [groupId]);
    },
    launchChatWtithClientGroupId: function(clientGroupid, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "launchChatWithClientGroupId", [clientGroupid]);
    },
    logout: function(successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ALChatManagerCDVPlugin", "logout", []);
    }
};
