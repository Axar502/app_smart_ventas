// Contenido para cada módulo, que cambiará dinámicamente en la página
const moduleContent = {
    clientes: `
        <form id="client-form">
            <input type="text" id="client-name" placeholder="Nombre del Cliente" required>
            <input type="email" id="client-email" placeholder="Correo Electrónico" required>
            <input type="tel" id="client-phone" placeholder="Teléfono" required>
            <button type="submit">Agregar Cliente</button>
        </form>
        <table id="client-table">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Teléfono</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    `,
    ventas: `
        <form id="sale-form">
            <input type="text" id="sale-product" placeholder="Producto" required>
            <input type="number" id="sale-quantity" placeholder="Cantidad" required>
            <input type="number" id="sale-price" placeholder="Precio" step="0.01" required>
            <button type="submit">Agregar Venta</button>
        </form>
        <table id="sale-table">
            <thead>
                <tr>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th>Total</th>
                    <th>Fecha</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    `,
    inventario: `
        <form id="inventory-form">
            <input type="text" id="inventory-name" placeholder="Nombre del Producto" required>
            <input type="number" id="inventory-quantity" placeholder="Cantidad" required>
            <input type="number" id="inventory-price" placeholder="Precio" step="0.01" required>
            <button type="submit">Agregar Producto</button>
        </form>
        <table id="inventory-table">
            <thead>
                <tr>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th>Valor Total</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    `,
    facturacion: `
        <form id="invoice-form">
            <input type="text" id="invoice-customer" placeholder="Nombre del Cliente" required>
            <input type="date" id="invoice-date" required>
            <button type="submit">Generar Factura</button>
        </form>
        <table id="invoice-table">
            <thead>
                <tr>
                    <th>ID Factura</th>
                    <th>Cliente</th>
                    <th>Fecha</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    `
};

// Estado local para manejar los datos de los módulos
const state = {
    clientes: [],
    ventas: [],
    inventario: [],
    facturacion: []
};

// Funciones auxiliares para formatear los datos
function formatCurrency(amount) {
    return `$${amount.toFixed(2)}`;
}

function formatDate(date) {
    return new Date(date).toLocaleDateString();
}

// Funciones para agregar datos a cada sección y actualizar las tablas

function addClient(name, email, phone) {
    const client = { id: Date.now(), name, email, phone };
    state.clientes.push(client);
    updateClientTable();
}

function addSale(product, quantity, price) {
    const sale = {
        id: Date.now(),
        product,
        quantity: Number(quantity),
        price: Number(price),
        total: quantity * price,
        date: new Date().toISOString()
    };
    state.ventas.push(sale);
    updateSaleTable();
}

function addInventoryItem(name, quantity, price) {
    const item = {
        id: Date.now(),
        name,
        quantity: Number(quantity),
        price: Number(price),
        totalValue: quantity * price
    };
    state.inventario.push(item);
    updateInventoryTable();
}

function addInvoice(customer, date) {
    const invoice = {
        id: Date.now(),
        customer,
        date,
        total: 0 // Simplificado por ahora, puedes agregar lógica para los productos de la factura
    };
    state.facturacion.push(invoice);
    updateInvoiceTable();
}

// Funciones para actualizar las tablas

function updateClientTable() {
    const tbody = document.querySelector('#client-table tbody');
    tbody.innerHTML = state.clientes.map(client => `
        <tr>
            <td>${client.name}</td>
            <td>${client.email}</td>
            <td>${client.phone}</td>
        </tr>
    `).join('');
}

function updateSaleTable() {
    const tbody = document.querySelector('#sale-table tbody');
    tbody.innerHTML = state.ventas.map(sale => `
        <tr>
            <td>${sale.product}</td>
            <td>${sale.quantity}</td>
            <td>${formatCurrency(sale.price)}</td>
            <td>${formatCurrency(sale.total)}</td>
            <td>${formatDate(sale.date)}</td>
        </tr>
    `).join('');
}

function updateInventoryTable() {
    const tbody = document.querySelector('#inventory-table tbody');
    tbody.innerHTML = state.inventario.map(item => `
        <tr>
            <td>${item.name}</td>
            <td>${item.quantity}</td>
            <td>${formatCurrency(item.price)}</td>
            <td>${formatCurrency(item.totalValue)}</td>
        </tr>
    `).join('');
}

function updateInvoiceTable() {
    const tbody = document.querySelector('#invoice-table tbody');
    tbody.innerHTML = state.facturacion.map(invoice => `
        <tr>
            <td>${invoice.id}</td>
            <td>${invoice.customer}</td>
            <td>${formatDate(invoice.date)}</td>
            <td>${formatCurrency(invoice.total)}</td>
        </tr>
    `).join('');
}

// Manejo de eventos y cambios dinámicos

document.addEventListener('DOMContentLoaded', () => {
    const moduleTitle = document.getElementById('module-title');
    const moduleContentElement = document.getElementById('module-content');
    const navButtons = document.querySelectorAll('.nav-button');

    function setActiveModule(moduleName) {
        moduleTitle.textContent = moduleName;
        moduleContentElement.innerHTML = moduleContent[moduleName.toLowerCase()];
        navButtons.forEach(button => {
            button.classList.toggle('active', button.dataset.module === moduleName.toLowerCase());
        });
    }

    navButtons.forEach(button => {
        button.addEventListener('click', () => {
            const moduleName = button.dataset.module;
            setActiveModule(moduleName.charAt(0).toUpperCase() + moduleName.slice(1));
        });
    });

    // Set initial module
    setActiveModule('Clientes');

    // Event delegation for form submissions
    moduleContentElement.addEventListener('submit', (e) => {
        e.preventDefault();
        const formId = e.target.id;

        switch (formId) {
            case 'client-form':
                addClient(
                    document.getElementById('client-name').value,
                    document.getElementById('client-email').value,
                    document.getElementById('client-phone').value
                );
                e.target.reset();
                break;
            case 'sale-form':
                addSale(
                    document.getElementById('sale-product').value,
                    document.getElementById('sale-quantity').value,
                    document.getElementById('sale-price').value
                );
                e.target.reset();
                break;
            case 'inventory-form':
                addInventoryItem(
                    document.getElementById('inventory-name').value,
                    document.getElementById('inventory-quantity').value,
                    document.getElementById('inventory-price').value
                );
                e.target.reset();
                break;
            case 'invoice-form':
                addInvoice(
                    document.getElementById('invoice-customer').value,
                    document.getElementById('invoice-date').value
                );
                e.target.reset();
                break;
        }
    });
});
