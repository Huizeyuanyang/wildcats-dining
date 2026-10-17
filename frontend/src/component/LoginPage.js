import { useState } from "react";
import { login } from "../APIs";

function LoginPage(props) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  function handleSubmit(e) {
    e.preventDefault();
    login(email, password).then((response) => {
      if (response.ok) {
        props.onLoginSuccess();
      } else {
        setError("Wrong email or password");
      }
    });
  }

  return (
    <div className="login-page">
        <div className="login-card">
        <h2>Welcome back</h2>
        <p className="login-subtitle">Log in to order your food</p>

        <form onSubmit={handleSubmit} className="login-form">
            <input
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            />
            <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            />
            <button type="submit" className="login-button">Log in</button>
        </form>

        {error && <p className="login-error">{error}</p>}

        <p>
            Don't have an account?{" "}
            <button onClick={props.onGoSignup} className="link-button">
                Sign up
            </button>
        </p>
        <button onClick={props.onBack} className="link-button">
            ← Back to restaurants
        </button>
        </div>
    </div>
    );
}

export default LoginPage;
