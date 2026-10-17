import './App.css';
import { FaPaw, FaShoppingCart, FaUser } from "react-icons/fa";
import {useState, useEffect} from "react"
import RestaurantCard from "./component/RestaurantCard";
import FoodCard from './component/FoodCard';
import LoginPage from './component/LoginPage';
import SignupPage from './component/SignupPage';
import CartDrawer from './component/CartDrawer';
import ProfilePage from './component/ProfilePage';
import ConfirmDialog from './component/ConfirmDialog';
import { getRestaurants, getCart, checkout, addToCart, removeFromCart, logout, getMe } from './APIs';

function App() {
  const [restaurants, setRestaurants] = useState([]);
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);
  const [showLogin, setShowLogin] = useState(false);
  const [user, setUser] = useState(null);
  const isLoggedIn = user !== null;
  const [showSignup, setShowSignup] = useState(false);
  const [isCartOpen, setIsCartOpen] = useState(false);
  const [cart, setCart] = useState(null);
  const [message, setMessage] = useState("");
  const [showProfile, setShowProfile] = useState(false);
  const [showLogoutConfirm, setShowLogoutConfirm] = useState(false);

  useEffect(() => {
    getRestaurants().then((data) => setRestaurants(data));
    getMe().then((data) => setUser(data));
  }, []);

  let content;
  if (showProfile) {
    content = (
      <ProfilePage
        user={user}
        onBack={() => setShowProfile(false)}
        onLogout={() => setShowLogoutConfirm(true)}
      />
    );
  } else if (showSignup) {
    content = (
      <SignupPage
        onGoLogin={() => {
          setShowSignup(false);
          setShowLogin(true);
        }}
        onSignupSuccess={() => {
          setShowSignup(false);
          setShowLogin(true);
        }}
      />
    );
  } else if (showLogin) {
    content = (
      <LoginPage
        onBack={() => setShowLogin(false)}
        onGoSignup={() => {
          setShowLogin(false);
          setShowSignup(true);
        }}
        onLoginSuccess={() => {
          getMe().then((data) => setUser(data));
          setShowLogin(false);
        }}
      />
    );
  } else if (selectedRestaurant == null) {
    content = (
        <div>
          <h2>Restaurant Around</h2>
          <div className="restaurant-list">
            {restaurants.map((restaurant) => (
              <RestaurantCard 
                key={restaurant.id}
                name={restaurant.name}
                imageUrl={restaurant.image_url}
                address={restaurant.address}
                phone={restaurant.phone}
                onSelect={() => setSelectedRestaurant(restaurant)}
              />
            )
            )}
          </div>
        </div>
      );
  } else {
    content = (
      <div>
        <button className="back-button" onClick={() => setSelectedRestaurant(null)}>
          ← All restaurants
        </button>
        <h2>{selectedRestaurant.name}</h2>
        <div className="food-list">
          {selectedRestaurant.menu_items.map((food) => (
            <FoodCard
              key={food.id}
              name={food.name}
              imageUrl={food.image_url}
              description={food.description}
              price={food.price}
              onAddToCart={() => handleAddToCart(food.id)}
            />
          )
          )}
        </div>
      </div>
    );
  }

  function openCart() {
    setIsCartOpen(true);
    getCart().then((data) => setCart(data));
  }

  function handleAddToCart(menuId) {
    if (!isLoggedIn) {
      setShowLogin(true);
      return;
    }
    addToCart(menuId).then((response) => {
      if (response.ok) {
        setMessage("Added to cart successfully!");
        getCart().then((data) => setCart(data));
        setTimeout(() => setMessage(""), 2000);
      } else {
        setMessage("Failed to add, please try again");
        setTimeout(() => setMessage(""), 2000);
      }
    });
  }
  function handleRemoveFromCart(menuId) {
    removeFromCart(menuId).then((response) => {
      if (response.ok) {
        setMessage("Remove From cart successfully!");
        getCart().then((data) => setCart(data));
        setTimeout(() => setMessage(""), 2000);
      } else {
        setMessage("Failed to remove, please try again");
        setTimeout(() => setMessage(""), 2000);
      }
    });
  }

  function handleLogout() {
    logout().then(() => {
      setUser(null);
      setCart(null);
      setIsCartOpen(false);
      setShowProfile(false);
    });
  }

  function goHome() {
    setShowLogin(false);
    setShowSignup(false);
    setSelectedRestaurant(null);
    setIsCartOpen(false);
    setShowProfile(false);
  }

  return (
    <div className="App">
      <header className="header">
        <h1 onClick={goHome}>
          WildCats Dining
        </h1>
        <div className="header-right">
          {isLoggedIn ? (
            <>
              <button className="header-button" onClick={() => setShowLogoutConfirm(true)}>Logout</button>
              <button className="header-button" onClick={() => setShowProfile(true)}>
                <FaPaw /> {user.first_name}
              </button>
            </>
          ) : (
            <button className="header-button" onClick={() => setShowLogin(true)}>
              <FaUser /> Login
            </button>
          )}
        </div>
      </header>

      <main className="content">
        {content}
      </main>

      <button className="floating-cart" onClick={openCart}>
        <FaShoppingCart /> Cart
      </button>

      {showLogoutConfirm && (
        <ConfirmDialog
          title="Log out?"
          message="Are you sure you want to log out?"
          confirmText="Log out"
          onCancel={() => setShowLogoutConfirm(false)}
          onConfirm={() => {
            setShowLogoutConfirm(false);
            handleLogout();
          }}
        />
      )}
      
      {message && <div className="toast">{message}</div>}
      {isCartOpen && (
        <CartDrawer
          cart={cart}
          onClose={() => setIsCartOpen(false)}
          onAddItem={(menuId) => handleAddToCart(menuId)}
          onRemoveItem={(menuId) => handleRemoveFromCart(menuId)}
          restaurants={restaurants}
          onCheckout={() => {
            checkout().then(() => getCart().then((data) => setCart(data)));
          }}
        />
      )}
        
    </div>

  
  );
}

export default App;
