const url = "http://localhost:8080";
let stompClient;
let selectedConversation;
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
                if (
                    selectedConversation === data.groupId &&
                    userName !== data.sender
                ) {
                    render(data.content, data.sender);
                    // let htmlText = `<li>
                    //         <div class="message-data">
                    //             <span class="message-data-name"><i class="fa fa-circle online"></i> ${data.sender}</span>
                    //             <span class="message-data-time">Today</span>
                    //         </div>
                    //         <div class="message my-message">
                    //             ${data.content}
                    //         </div>
                    //     </li>`;
                    // $chatHistory = $(".chat-history");
                    // $chatHistoryList = $chatHistory.find("ul");
                    // $chatHistoryList.append(htmlText);
                } else {
                    // console.log("thong bao  + 1");
                    // newMessages.set(data.fromLogin, data.message);
                    $(`#userNameAppender_${data.groupId}`).append(
                        `<span id="newMessage_${data.fromLogin}" style="color: red">+1</span>`
                    );
                }
            }
        );
    });
}

function sendMsg(from, text) {
    stompClient.send(
        "/app/chat/" + selectedConversation,
        {},
        JSON.stringify({
            fromLogin: from,
            message: text,
        })
    );
}

function registration() {
    let userName = document.getElementById("userName").value;
    $.get(url + "/registration/user/" + userName, function (response) {
        connectToChat(userName);
    }).fail(function (error) {
        if (error.status === 400) {
            alert("Login is already busy!");
        }
    });
}

function registrationConverSation() {
    let conversationId = document.getElementById("conversationid").value;
    $.get(
        url + "/registration/conversation/" + conversationId,
        function (response) {
            // connectToChat(userName);
        }
    ).fail(function (error) {
        if (error.status === 400) {
            alert("Login is already busy!");
        }
    });
}

function selectUser(conversationId) {
    console.log(conversationId);
    selectedConversation = conversationId;
    let isNew =
        document.getElementById(`newMessage_${conversationId}`) !== null;
    if (isNew) {
        let element = document.getElementById(`newMessage_${conversationId}`);
        element.parentNode.removeChild(element);
        render(newMessages.get(conversationId), conversationId);
    }
    $("#selectedUser").html("");
    $("#selectedUser").append("Chat with " + conversationId);
}

function fetchAll() {
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
}
