document.addEventListener("DOMContentLoaded", (evt) => {
    const closeBtn = document.querySelector(".modal-close");
    if (closeBtn) {
        closeBtn.addEventListener("click", () => {
            document.getElementById("orderDetailModal").style.display = "none";
        });
    }

    document.querySelectorAll(".order-detail-btn").forEach((btn) => {
        btn.addEventListener("click", async () => {
            const orderId = btn.dataset.id;

            try {
                const res = await fetch(`${contextPath}/sales/orderDetail.page?orderId=${orderId}`);
                const data = await res.json();

                document.getElementById("orderIdDisplay").textContent = "주문번호: " + data.orderId;
                document.getElementById("orderStatus").textContent = data.orderStatus;
                document.getElementById("paymentMethod").textContent = data.payment;
                document.getElementById("totalCount").textContent = data.orderTotalCount + "권";
                document.getElementById("totalPrice").textContent = data.orderTotalPrice.toLocaleString() + "원";
                document.getElementById("customerIdModal").textContent = data.customer.customerId;
                document.getElementById("orderDate").textContent = data.orderDate;
                document.getElementById("updateDate").textContent = data.updateDate;
                document.getElementById("orderMemo").textContent = data.orderMemo || "-";
                document.getElementById("customerName").textContent = data.customer.customerName;
                document.getElementById("customerAddress").textContent = data.customer.customerAddress;

                const tbody = document.getElementById("productTableBody");
                tbody.innerHTML = "";

                data.orderDetailList.forEach((detail) => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
            <td>${detail.orderDetailId}</td>
            <td>${detail.bookId}</td>
            <td>${detail.bookName} <div style="font-size:12px; color:#888;">${detail.authorName}</div></td>
            <td style="text-align:right;">${detail.orderDetailTotalCount}</td>
            <td style="text-align:right;">${detail.orderDetailTotalPrice.toLocaleString()}원</td>
          `;
                    tbody.appendChild(row);
                });

                document.getElementById("grandTotal").textContent = data.orderTotalPrice.toLocaleString() + "원";

                document.getElementById("orderDetailModal").style.display = "block";
            } catch (err) {
                alert("주문 상세 조회에 실패했습니다.");
                console.error(err);
            }
        });
    });
});