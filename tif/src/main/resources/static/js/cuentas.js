(async () => {
  const u = localStorage.getItem("username");

  // Obtener cliente
  const cRes = await fetch(`/api/clientes/by-username/${u}`, {
    headers: authHeaders()
  });
  const cliente = await cRes.json();

  // Obtener cuentas
  const resp = await fetch(`/api/clientes/${cliente.id}/cuentas`, {
    headers: authHeaders()
  });
  const cuentas = await resp.json();

  const lista = document.getElementById("listaCuentas");
  cuentas.forEach(c => {
    const li = document.createElement("li");
    li.className = "list-group-item";
    li.textContent = `${c.numeroCuenta} (${c.tipoCuenta}) - $${c.saldo}`;
    lista.appendChild(li);
  });

  // Manejar el form de nueva cuenta
  document.getElementById("formCrearCuenta").addEventListener("submit", async (e) => {
    e.preventDefault();
    const numeroCuenta = document.getElementById("numeroCuenta").value;
    const tipo = document.getElementById("tipoCuenta").value;
    const saldo = document.getElementById("saldo").value;

    const nuevaCuenta = {
      numeroCuenta: numeroCuenta,
      tipoCuenta: tipo,
      saldo: parseFloat(saldo)
    };

    const postRes = await fetch("/api/cuentas/mi-cuenta", {
      method: "POST",
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
        ...authHeaders()
      },
      body: JSON.stringify(nuevaCuenta)
    });

    if (postRes.ok) {
      window.location.reload();
    } else {
      alert("❌ Error creando cuenta");
    }
  });
})();
