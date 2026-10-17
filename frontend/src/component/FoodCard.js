function FoodCard(props) {
  return (
    <div className="food-card">
      <img src={props.imageUrl} alt={props.name} className="food-image" />
      <div className="food-info">
        <h3 className="food-name">{props.name}</h3>
        <p className="food-description">{props.description}</p>
        <div className="food-bottom">
          <p className="food-price">${props.price.toFixed(2)}</p>
          <button className="add-button" onClick={props.onAddToCart}>
            Add to cart
          </button>
        </div>
      </div>
    </div>
  );
}

export default FoodCard;