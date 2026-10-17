function CartDrawer(props) {
  const cart = props.cart;

  let body;
  if (cart === null) {
    body = <p className="cart-empty">Please log in first</p>;
  } else if (cart.order_items.length === 0) {
    body = <p className="cart-empty">Your cart is empty</p>;
  } else {
    // Group items by restaurant: { restaurantId: [items of that restaurant] }
    const groups = {};
    for (const item of cart.order_items) {
      if (!groups[item.restaurant_id]) {
        groups[item.restaurant_id] = [];
      }
      groups[item.restaurant_id].push(item);
    }

    body = (
      <div className="cart-body">
        <div className="cart-items">
          {Object.keys(groups).map((restaurantId) => {
            const restaurant = props.restaurants.find((r) => r.id === Number(restaurantId));
            return (
              <div key={restaurantId} className="cart-group">
                <h4 className="cart-group-title">
                  {restaurant ? restaurant.name : "Restaurant"}
                </h4>
                {groups[restaurantId].map((item) => (
                  <div className="cart-item" key={item.order_item_id}>
                    <img src={item.menu_item_image_url} alt={item.menu_item_name} />
                    <div className="cart-item-info">
                      <p className="cart-item-name">{item.menu_item_name}</p>
                      <div className="qty-control">
                        <button onClick={() => props.onRemoveItem(item.menu_item_id)}>−</button>
                        <span>{item.quantity}</span>
                        <button onClick={() => props.onAddItem(item.menu_item_id)}>+</button>
                      </div>
                    </div>
                    <p className="cart-item-price">
                      ${(item.price * item.quantity).toFixed(2)}
                    </p>
                  </div>
                ))}
              </div>
            );
          })}
        </div>

        <div className="cart-footer">
          <div className="cart-total">
            <span>Total</span>
            <span>${cart.total_price.toFixed(2)}</span>
          </div>
          <button className="checkout-button" onClick={props.onCheckout}>
            Checkout
          </button>
        </div>
      </div>
    );
  }

    return (
    <>
        <div className="cart-overlay" onClick={props.onClose}></div>
        <div className="cart-drawer">
        <div className="cart-header">
            <h2>Your Cart</h2>
            <button className="cart-close" onClick={props.onClose}>✕</button>
        </div>
        {body}
        </div>
    </>
    );
}

export default CartDrawer;