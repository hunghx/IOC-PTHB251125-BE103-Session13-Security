// sau khi đăng nhập thì client nhận token
// token sẽ đc lưu ở đâu : localStorge

let token = localStorage.getItem("accessToken");
fetch("api/v1/admin/courses", {
    headers:{
        "Authorization": `Bearer ${token}`
    },
    method: "POST",
    body : {
        "usename":"jsdhfj"
    }
})
// nhận đc data nếu token hợp