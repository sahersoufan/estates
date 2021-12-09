
function login(req) {

    fetch(req).then(function (response) {
// The API call was successful!
        return response.json();
    }).then(function (json){
        if (json.succ){
            document.location = 'http://localhost:8080/'
        }else{
            throw err;
        }
    }).catch(function (err) {
        location.reload();
    });
}
function checkExpire() {
    const checkReq = new Request('http://localhost:8080/api/checkExpire',{method:'GET'});
    fetch(checkReq).then(function (response) {
        return response.json();
    }).then(function (json) {

        if (!json.res){
            document.location = 'http://localhost:8080/loginPublic'
            return true;
        }
        if (json.res && json.reload){
            location.reload();
            return false;
        }
    })

}
/*

function checkExpire() {
    if ($.cookie('access_token') != null){
    const accessToken = $.cookie('access_token');
    const refreshToken = $.cookie('refresh_token');

    const date = new Date(0);
    const refreshDate = new Date(0);

    const decoded = parseJwt(accessToken);
    const refreshDecoded = parseJwt(refreshToken);

    date.setUTCSeconds(decoded.exp);
    refreshDate.setUTCSeconds(refreshDecoded.exp);

    if (date.valueOf() - 30*1000 < new Date().valueOf()) {

        if (refreshDate.valueOf() - 60*1000 < new Date().valueOf()){
            document.location = 'http://localhost:8080/loginPublic';
            return ;
        }

        callRefreshToken();
        return true;
    }
    }else {
        document.location = 'http://localhost:8080/loginPublic'
    }
}*/

function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}


function callRefreshToken() {
    const refreshRequest = new Request('http://localhost:8080/api/token/refresh',{method:'GET'});
    fetch(refreshRequest).then(function (response) {
        return response.json();
    }).then(function (json) {
        $.cookie('access_token', json.access_token, {path:'/'});
        $.cookie('refresh_token', json.refresh_token, {path:'/'});
    })
}

function getAnotherPage(req) {

        fetch(req).then(function (response) {
// The API call was successful!
            if(response.status === 403){
                throw err;
            }
            return response.text();
        }).then(function (html) {
// This is the HTML from our response as a text string
/*
            $("#Content").html(html);
*/
            $('body').append(html);
        }).catch(function (err) {
// There was an error
            console.log('Something went wrong.', err);
        });

}

function postAnotherPage(req) {

        fetch(req).then(function (response) {
// The API call was successful!
            if(response.status === 403){
                throw err;
            }
            location.reload();
            return response.text();
        }).catch(function (err) {
// There was an error
            console.log('Something went wrong.', err);
            checkExpire();
        });

}

