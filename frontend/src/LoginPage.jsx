import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Auth.css';

const LoginPage = ({ handleLogin }) => {
  const [isRegistering, setIsRegistering] = useState(false);
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    firstname: '', lastname: '', country: '', city: '',
    street: '', postalNumber: '', phoneNumber: '',
    mail: '', login: '', password: ''
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // --- FUNKCJA LOGOWANIA ---
  const onLogin = async (e) => {
    e.preventDefault();
    try {
      // Zmieniony endpoint na /login (dostosuj do swojego API)
      const response = await fetch('http://localhost:8080/user/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          login: formData.login,
          password: formData.password
        })
      });

      if (!response.ok) throw new Error('Błędny login lub hasło');

      const data = await response.json();
      console.log('Zalogowano pomyślnie:', data);
      
      handleLogin(data); // Przekazujemy dane użytkownika z serwera
      navigate('/profil');
    } catch (error) {
      console.error('Błąd logowania:', error);
      alert('Nie udało się zalogować. Sprawdź dane.');
    }
  };

  // --- FUNKCJA REJESTRACJI ---
  const onRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/user/register/user', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
      });

      if (!response.ok) throw new Error(`Błąd rejestracji: ${response.status}`);

      const data = await response.json();
      console.log('Rejestracja udana:', data);

      // Po rejestracji możemy od razu zalogować użytkownika
      handleLogin(data);
      navigate('/profil');
    } catch (error) {
      console.error('Błąd rejestracji:', error);
      alert('Błąd podczas tworzenia konta.');
    }
  };

  // --- WIDOK REJESTRACJI ---
  if (isRegistering) {
    return (
      <div className="auth-container">
        <div className="auth-box registration-box">
          <h2>ZAREJESTRUJ SIĘ</h2>
          <form onSubmit={onRegister}> {/* <--- Wywołuje onRegister */}
            <div className="input-group"><label>Imię</label><input type="text" name="firstname" required onChange={handleChange} /></div>
            <div className="input-group"><label>Nazwisko</label><input type="text" name="lastname" required onChange={handleChange} /></div>
            <div className="input-group"><label>Kraj</label><input type="text" name="country" required onChange={handleChange} /></div>
            <div className="input-group"><label>Miasto</label><input type="text" name="city" required onChange={handleChange} /></div>
            <div className="input-group"><label>Ulica</label><input type="text" name="street" required onChange={handleChange} /></div>
            <div className="input-group"><label>Kod pocztowy</label><input type="text" name="postalNumber" required onChange={handleChange} /></div>
            <div className="input-group"><label>Telefon</label><input type="tel" name="phoneNumber" required onChange={handleChange} /></div>
            <div className="input-group"><label>Email</label><input type="email" name="mail" required onChange={handleChange} /></div>
            <div className="input-group"><label>Login</label><input type="text" name="login" required onChange={handleChange} /></div>
            <div className="input-group"><label>Hasło</label><input type="password" name="password" required onChange={handleChange} /></div>
            <button type="submit" className="auth-btn">ZAŁÓŻ KONTO</button>
          </form>
          <p className="toggle-text">
            Masz już konto? <span onClick={() => setIsRegistering(false)}>Zaloguj się</span>
          </p>
        </div>
      </div>
    );
  }

  // --- WIDOK LOGOWANIA ---
  return (
    <div className="auth-container">
      <div className="auth-box">
        <h2>ZALOGUJ SIĘ</h2>
        <form onSubmit={onLogin}> {/* <--- Wywołuje onLogin */}
          <div className="input-group">
            <label>Login</label>
            <input type="text" name="login" required onChange={handleChange} />
          </div>
          <div className="input-group">
            <label>Hasło</label>
            <input type="password" name="password" required onChange={handleChange} />
          </div>
          <button type="submit" className="auth-btn">ZALOGUJ SIĘ</button>
        </form>
        <p className="toggle-text">
          Nie masz konta? <span onClick={() => setIsRegistering(true)}>Zarejestruj się</span>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;