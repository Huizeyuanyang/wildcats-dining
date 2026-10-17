import { useState } from "react";
import { signup } from "../APIs";

function SignupPage(props) {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  function handleSubmit(e) {
    e.preventDefault();
    signup(email, password, firstName, lastName).then((response) => {
      if (response.ok) {
        props.onSignupSuccess();
      } else {
        setError("Sign up failed. This email may already be registered.");
      }
    });
  }

  return (
    <div className="login-page">
      <div className="login-card">
        <h2>Create account</h2>
        <p className="login-subtitle">Join WildCats Dining</p>

        <form onSubmit={handleSubmit} className="login-form">
          <input placeholder="First name" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
          <input placeholder="Last name" value={lastName} onChange={(e) => setLastName(e.target.value)} />
          <input placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
          <button type="submit" className="login-button">Sign up</button>
        </form>

        {error && <p className="login-error">{error}</p>}

        <button onClick={props.onGoLogin} className="link-button">
          Already have an account? Log in
        </button>
      </div>
    </div>
  );
}

export default SignupPage;