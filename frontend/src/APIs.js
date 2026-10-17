export function getRestaurants() {
  return fetch("/restaurants/menu").then((response) => response.json());
}

export function login(email, password) {
  return fetch("/login", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({ username: email, password: password }),
  });
}

export function signup(email, password, firstName, lastName) {
  return fetch("/signup", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      email: email,
      password: password,
      first_name: firstName,
      last_name: lastName,
    }),
  });
}

export function getMe() {
  return fetch("/me").then((response) => (response.ok ? response.json() : null));
}

export function getCart() {
  return fetch("/cart").then((response) => (response.ok ? response.json() : null));
}

export function addToCart(menuId) {
  return fetch("/cart", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ menu_id: menuId }),
  });
}

export function removeFromCart(menuId) {
  return fetch("/cart/decrease", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ menu_id: menuId }),
  });
}

export function checkout() {
  return fetch("/cart/checkout", { 
    method: "POST" 
  });
}

export function logout() {
  return fetch("/logout", { method: "POST" });
}

export function getOrders() {
  return fetch("/orders").then((response) => (response.ok ? response.json() : []));
}
