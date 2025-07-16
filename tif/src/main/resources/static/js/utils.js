// js/utils.js

function authHeaders() {
  return {
    Authorization: `Bearer ${localStorage.getItem("token")}`
  };
}

function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("username");
  window.location.href = "../html/login.html";
}
