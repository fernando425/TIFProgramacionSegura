(async () => {
    const lista = document.getElementById("listaMovimientos");

    try {
        const resp = await fetch("/api/transferencias/mis-movimientos", {
            headers: authHeaders()
        });

        const transferencias = await resp.json();

        if (!Array.isArray(transferencias)) {
            lista.innerHTML = '<li class="list-group-item text-danger">❌ Error al cargar movimientos.</li>';
            return;
        }

        if (transferencias.length === 0) {
            lista.innerHTML = '<li class="list-group-item text-muted">No hay movimientos registrados.</li>';
            return;
        }

        transferencias.forEach(t => {
            const li = document.createElement("li");
            li.className = "list-group-item d-flex justify-content-between align-items-center";
            li.innerHTML = `
                <div>
                    <strong>Desde:</strong> ${t.cuentaOrigen?.numeroCuenta ?? 'N/A'} →
                    <strong>Hacia:</strong> ${t.cuentaDestino?.numeroCuenta ?? 'N/A'}<br>
                    <small class="text-muted">${new Date(t.fecha).toLocaleString()}</small>
                </div>
                <span class="badge bg-success fs-6">$${t.monto}</span>
            `;
            lista.appendChild(li);
        });
    } catch (error) {
        console.error("Error al cargar movimientos:", error);
        lista.innerHTML = '<li class="list-group-item text-danger">❌ Error de red o de servidor.</li>';
    }
})();
