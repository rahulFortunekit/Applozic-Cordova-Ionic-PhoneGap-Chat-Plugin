/*global cordova, module*/

module.exports = {
    login: function(alUser, successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "login", [JSON.stringify(alUser)]);
    },
    isLoggedIn: function(successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "isLoggedIn", []);
    },
    updatePushNotificationToken: function(token, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "updatePushNotificationToken", [token]);
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
    startNewConversation: function(selected, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "startNewConversation", [selected]);
    },
    addContact: function(contact, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "addContact", [JSON.stringify(contact)]);
    },
    updateContact: function(contact, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "updateContact", [JSON.stringify(contact)]);
    },
    removeContact: function(contact, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "removeContact", [JSON.stringify(contact)]);
    },
    addContacts: function(contacts, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "addContacts", [JSON.stringify(contacts)]);
    },
    logout: function(successCallback, errorCallback) {
    	cordova.exec(successCallback, errorCallback, "ApplozicCordovaPlugin", "logout", []);
    }
};
