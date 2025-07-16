document.getElementById("transferForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const o = document.getElementById("origen").value;
  const d = document.getElementById("destino").value;
  const m = parseFloat(document.getElementById("monto").value);

  const res = await fetch("/api/transferencias/realizar", {
    method: "POST",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      ...authHeaders()
    },
    body: JSON.stringify({ origen: o, destino: d, monto: m })
  });
  document.getElementById("resultado").textContent =
    res.ok ? "Transferencia exitosa" : "Error en transferencia";
});
