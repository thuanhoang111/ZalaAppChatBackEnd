const url = "http://localhost:8080";
let stompClient;
let selectedUser;
let newMessages = new Map();

$(document).ready(function () {
    $.get(url + "/fetchAllConversations", function (response) {
        let conversations = response;
        let conversationsTemplateHTML = "";
        for (let i = 0; i < conversations.length; i++) {
            conversationsTemplateHTML =
                conversationsTemplateHTML +
                `<a href="#" onClick="selectUser('${conversations[i].id}')"><li class="clearfix">
                    <img alt="avatar" height="55px"
                        src="https://rtfm.co.ua/wp-content/plugins/all-in-one-seo-pack/images/default-user-image.png"
                        width="55px"/>
                    <div class="about">
                        <div id="userNameAppender_${conversations[i].id}" class="name">${conversations[i].id}</div>
                        <div class="status">
                            <i class="fa fa-circle online"></i>
                        </div>
                    </div>
                </li></a>`;
        }
        $("#usersList").html(conversationsTemplateHTML);
    });
});

function connectToChat(userName) {
    console.log("connecting to chat...");
    let socket = new SockJS(url + "/chat");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe(
            "/topic/messages/" + userName,
            function (response) {
                let data = JSON.parse(response.body);
                console.log(data);
                if (selectedUser === data.fromLogin) {
                    // render(data.message, data.fromLogin);
                    let htmlText = `<li>
                            <div class="message-data">
                                <span class="message-data-name"><i class="fa fa-circle online"></i> ${data.fromLogin}</span>
                                <span class="message-data-time">Today</span>
                            </div>
                            <div class="message my-message">
                                ${data.message}
                            </div>
                        </li>`;
                    $chatHistory = $(".chat-history");
                    $chatHistoryList = $chatHistory.find("ul");
                    $chatHistoryList.append(htmlText);
                } else {
                    console.log("thong bao  + 1");
                    newMessages.set(data.fromLogin, data.message);
                    $(`#userNameAppender_${data.fromLogin}`).append(
                        `<span id="newMessage_${data.fromLogin}" style="color: red">+1</span>`
                    );
                }
            }
        );
    });
}

function sendMsg(from, text) {
    stompClient.send(
        "/app/chat/" + selectedUser,
        {},
        JSON.stringify({
            fromLogin: from,
            message: text,
        })
    );
}

function registration() {
    let userName = document.getElementById("userName").value;
    $.get(url + "/registration/" + userName, function (response) {
        connectToChat(userName);
    }).fail(function (error) {
        if (error.status === 400) {
            alert("Login is already busy!");
        }
    });
}

function selectUser(userName) {
    console.log(userName);
    selectedUser = userName;
    let isNew = document.getElementById(`newMessage_${userName}`) !== null;
    if (isNew) {
        let element = document.getElementById(`newMessage_${userName}`);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    }
    $("#selectedUser").html("");
    $("#selectedUser").append("Chat with " + userName);
}

function fetchAll() {
    $.get(url + "/fetchAllUsers", function (response) {
        let users = response;
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            usersTemplateHTML =
                usersTemplateHTML +
                `<a href="#" onClick="selectUser('${users[i]}')"><li class="clearfix">
                    <img alt="avatar" height="55px"
                        src="https://rtfm.co.ua/wp-content/plugins/all-in-one-seo-pack/images/default-user-image.png"
                        width="55px"/>
                    <div class="about">
                        <div id="userNameAppender_${users[i]}" class="name">${users[i]}</div>
                        <div class="status">
                            <i class="fa fa-circle online"></i>
                        </div>
                    </div>
                </li></a>`;
        }
        $("#usersList").html(usersTemplateHTML);
    });
}
