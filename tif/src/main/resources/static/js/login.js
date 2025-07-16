document.getElementById("loginForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const u = document.getElementById("username").value;
  const p = document.getElementById("password").value;
  try {
    const res = await fetch("/auth/login", {
      method: "POST",
      mode: "cors",
      headers: {"Content-Type":"application/json"},
      body: JSON.stringify({ username: u, password: p })
    });
    if (!res.ok) throw new Error("Login incorrecto");
    const data = await res.json();
    localStorage.setItem("token", data.jwt);
    localStorage.setItem("username", data.username);
    window.location.href = "../html/home.html";
  } catch (e) {
    document.getElementById("loginError").textContent = e.message;
  }
});
