/**
 * Created by codedrinker on 2019/6/1.
 */


function subtext(text) {
    $('#maintext').each(function() {
        var words = $(this).text().length;
        if(words > 15){
            $(this).text($(this).text().slice(0,15)+"...");
        }
    });
}

//帖子回复按钮
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    
    if (!content) {
        alert("不能回复空内容(前端校验)");
        return;
    }

    comment2target(questionId, 1, content);
}

//楼中楼回复按钮
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

//用作回复  targetId这个回复的id   type决定了这次回复是什么回复类型  content回复内容
function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        // 本项目用的是fastjson 获取后进行了强转 需要stringify来序列化？
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                // 重新刷新页面 200代表Ok
                window.location.reload();
            } else {
                //这里逻辑上应该不会出现  但是做一个预备
                if (response.code == 2003) {
                    alert(response.message);
                    window.open("http://localhost:8887/user/login");
                    // var isAccepted = confirm(response.message);
                    // if (isAccepted) {
                    //     window.open("https://github.com/login/oauth/authorize?client_id=2859958f9f059979ed3a&redirect_uri=" + document.location.origin + "/callback&scope=user&state=1");
                    //     window.localStorage.setItem("closable", true);
                    // }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}




// // 展开二级评论
// function collapseComments(e) {
//     //获取 e 这个标签下的 data-id 的属性
//     var id = e.getAttribute("data-id");
//     var comments = $("#comment-" + id);
//
//     //同理  判断其状态
//     var collapse = e.getAttribute("data-collapse");
//
//     if (collapse){
//         //这个玩意存在（开始时并不存在）   关闭
//         comments.removeClass("in");
//         e.removeAttribute("data-collapse");
//         e.classList.remove("active");
//     } else {
//         //开始默认是关闭的
//         var subCommentContainer = $("#comment-" + id);
//         comments.addClass("in");
//
//         // 标记二级评论展开状态
//         e.setAttribute("data-collapse", "in");
//         e.classList.add("active");
//     }
// }


function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论（如果有）
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            //如有json返回 刷新回复
            $.getJSON("/comment/" + id, function (data) {
                //填充楼中楼内容 用js 的方式把html拼出来（头很大）仿照question.html 128行开始 data.data 是因为resultVo下的数据叫data
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded tou",
                        "src": comment.user.avatar_url
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.creattime).format('YYYY-MM-DD')
                        //这里使用了moment.js的外部辅助工具 帮助js翻译时间戳
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}
//
function showSelectTag() {
    $("#select-tag").show();
}

function hideSelectTag() {
    $("#select-tag").hide();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}