var app = angular.module("app");
app.service('stompService', function () {
    var subscriptions = [];
    var client = Stomp.client('ws://localhost:8080/quiz');
    var connectedCallback = function () {
        console.log('connected');
        for (var i=0; i < subscriptions.length; i++) {
            var subscription = subscriptions[i];
            client.subscribe(subscription.topic, function(message) {
                subscription.callback(message);
            })
        }
        //client.send('/app/test', {}, 'Hello');
        //client.onopen = () => client.send('/app/test', {}, 'Hello');
    }
    var errorCallback = function () {
        console.log('error');
    }
    client.connect({}, connectedCallback, errorCallback);

    this.subscribe = function(topic, callback) {
        subscriptions.push({"topic":topic, "callback":callback})
    };

    this.publish = function (topic, object) {
        client.send(topic, {}, JSON.stringify(object));
    }
});