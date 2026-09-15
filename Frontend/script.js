const API_BASE_URL = "http://localhost:8080/api/users";


// ======================================================
// ADD USER
// ======================================================

document
    .getElementById("userForm")
    .addEventListener("submit", async function (event) {

        event.preventDefault();

        const uid = document.getElementById("uid").value.trim();
        const name = document.getElementById("name").value.trim();
        const city = document.getElementById("city").value.trim();
        const country = document.getElementById("country").value.trim();

        const message = document.getElementById("message");

        const userData = {
            uid: uid,
            name: name,
            city: city,
            country: country
        };

        try {

            message.textContent = "Adding user...";

            const response = await fetch(API_BASE_URL, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(userData)

            });

            if (!response.ok) {

                const errorText = await response.text();

                throw new Error(
                    errorText || "Failed to add user"
                );
            }

            const result = await response.json();

            message.textContent = "User added successfully!";

            message.style.color = "green";

            document.getElementById("userForm").reset();

            console.log("Created user:", result);

            // Automatically reload normal users
            loadUsers("normal");

        } catch (error) {

            console.error("Add user error:", error);

            message.textContent =
                "Error: " + error.message;

            message.style.color = "red";
        }

    });


// ======================================================
// LOAD USERS
// ======================================================

async function loadUsers(type) {

    let url = API_BASE_URL + "/" + type;

    let queryName = "";

    switch (type) {

        case "normal":
            queryName = "Normal Query";
            break;

        case "optimized":
            queryName = "Optimized Query (JOIN FETCH)";
            break;

        case "cached":
            queryName = "Cached Query";
            break;

        case "native":
            queryName = "Native SQL Query";
            break;

        case "sort/id":
            queryName = "Sorted by ID";
            break;

        case "sort/name":
            queryName = "Sorted by Name";
            break;

        default:
            queryName = type;
    }

    const queryInfo = document.getElementById("queryInfo");

    queryInfo.textContent =
        "Loading: " + queryName + "...";

    try {

        const startTime = performance.now();

        const response = await fetch(url);

        const endTime = performance.now();

        if (!response.ok) {

            const errorText = await response.text();

            throw new Error(
                errorText || "Request failed"
            );
        }

        const data = await response.json();

        console.log(queryName, data);

        displayUsers(data);

        const timeTaken =
            (endTime - startTime).toFixed(2);

        queryInfo.textContent =
            `${queryName} | Response time: ${timeTaken} ms | Records: ${data.length}`;

    } catch (error) {

        console.error("Query error:", error);

        queryInfo.textContent =
            "Error: " + error.message;

        document.getElementById("userTableBody").innerHTML = `
            <tr>
                <td colspan="5" class="empty">
                    Unable to load users.
                    Make sure Spring Boot is running on port 8080.
                </td>
            </tr>
        `;
    }
}


// ======================================================
// DISPLAY USERS
// ======================================================

function displayUsers(users) {

    const tableBody =
        document.getElementById("userTableBody");

    tableBody.innerHTML = "";

    if (!users || users.length === 0) {

        tableBody.innerHTML = `
            <tr>
                <td colspan="5" class="empty">
                    No users found.
                </td>
            </tr>
        `;

        return;
    }

    users.forEach(function (user) {

        let id = user.id ?? "";
        let uid = user.uid ?? "";
        let name = user.name ?? "";
        let city = user.city ?? "";
        let country = user.country ?? "";

        /*
         * Native SQL may return an array instead of
         * a normal User object.
         *
         * Expected native query format:
         * [id, uid, name, city, country]
         */

        if (Array.isArray(user)) {

            id = user[0] ?? "";
            uid = user[1] ?? "";
            name = user[2] ?? "";
            city = user[3] ?? "";
            country = user[4] ?? "";

        }

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${escapeHtml(id)}</td>
            <td>${escapeHtml(uid)}</td>
            <td>${escapeHtml(name)}</td>
            <td>${escapeHtml(city)}</td>
            <td>${escapeHtml(country)}</td>
        `;

        tableBody.appendChild(row);

    });
}


// ======================================================
// CLEAR TABLE
// ======================================================

function clearTable() {

    document.getElementById("userTableBody").innerHTML = `
        <tr>
            <td colspan="5" class="empty">
                No users loaded
            </td>
        </tr>
    `;

    document.getElementById("queryInfo").textContent =
        "Table cleared.";
}


// ======================================================
// BASIC HTML ESCAPING
// ======================================================

function escapeHtml(value) {

    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}