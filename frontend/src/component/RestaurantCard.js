function RestaurantCard(props) {
  return (
    <div className="restaurant-card" onClick={props.onSelect}>
      <img src={props.imageUrl} alt={props.name} className="restaurant-image" />
      <div className="restaurant-info">
        <h3 className="restaurant-name">{props.name}</h3>
        <p className="restaurant-detail">{props.address}</p>
        <p className="restaurant-detail">{props.phone}</p>
      </div>
    </div>
  );
}

export default RestaurantCard;