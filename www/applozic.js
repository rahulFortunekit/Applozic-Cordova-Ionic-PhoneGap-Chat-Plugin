/*global cordova, module*/

module.exports = {
    login: function(alUser, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "login", [JSON.stringify(alUser)]);
    },
    isLoggedIn: function(successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "isLoggedIn", []);
    },
    launchChat: function(successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "launchChat", []);
    },
    launchChatWithUserId: function(userId, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "launchChatWithUserId", [userId]);
    },
    launchChatWithGroupId: function(groupId, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "launchChatWithGroupId", [groupId]);
    },
    launchChatWithClientGroupId: function(clientGroupid, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "launchChatWithClientGroupId", [clientGroupid]);
    },
    logout: function(successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "logout", []);
    }
};
