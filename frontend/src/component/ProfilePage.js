import { useState, useEffect } from "react";
import { getOrders } from "../APIs";

function ProfilePage(props) {
  const user = props.user;
  const initials = user.first_name[0] + user.last_name[0];
  const [orders, setOrders] = useState([]);

  // 页面一打开，就去后端拿订单
  useEffect(() => {
    getOrders().then((data) => setOrders(data));
  }, []);

  return (
    <div className="profile-page">
      <div className="login-card">
        <div className="profile-avatar">{initials}</div>
        <h2>{user.first_name} {user.last_name}</h2>
        <p className="login-subtitle">{user.email}</p>

        <button className="login-button profile-logout" onClick={props.onLogout}>
          Log out
        </button>

        <button className="link-button" onClick={props.onBack}>
          ← Back to restaurants
        </button>
      </div>

      <div className="orders-section">
        <h2>My Orders</h2>

        {orders.length === 0 ? (
          <p className="orders-empty">No orders yet</p>
        ) : (
          orders.map((order) => (
            <div className="order-card" key={order.id}>
              <div className="order-header">
                <span>Order #{order.id}</span>
                <span className="order-date">
                  {new Date(order.created_at).toLocaleString()}
                </span>
              </div>

              {order.lines.map((line) => (
                <div className="order-line" key={line.id}>
                  <span>{line.menu_item_name} × {line.quantity}</span>
                  <span>${(line.price * line.quantity).toFixed(2)}</span>
                </div>
              ))}

              <div className="order-total">
                <span>Total</span>
                <span>${order.total_price.toFixed(2)}</span>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default ProfilePage;