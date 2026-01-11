import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Auth.css';

const ProfilePage = ({ user, handleLogout }) => {
  const navigate = useNavigate();

  const onLogout = () => {
    handleLogout();
    navigate('/');
  };

  if (!user) {
    return (
      <div className="auth-container">
        <h2>Nie jesteś zalogowany.</h2>
        <button className="auth-btn" onClick={() => navigate('/logowanie')}>
          Przejdź do logowania
        </button>
      </div>
    );
  }

  return (
    <div className="profile-container" style={{ padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
      <div className="profile-header" style={{ textAlign: 'center', marginBottom: '30px' }}>
        <h1>Witaj, {user.firstname}!</h1>
        <p>To jest Twój panel klienta (Login: <strong>{user.login}</strong>)</p>
      </div>

      <div className="profile-content" style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
        {/* Sekcja Danych z Twojego Fetcha */}
        <div className="profile-card" style={{ border: '1px solid #ddd', padding: '20px', borderRadius: '8px' }}>
          <h3>Twoje Dane</h3>
          <p><strong>Imię i Nazwisko:</strong> {user.firstname} {user.lastname}</p>
          <p><strong>Email:</strong> {user.mail}</p>
          <p><strong>Telefon:</strong> {user.phoneNumber}</p>
          <hr />
          <p><strong>Adres:</strong></p>
          <p>{user.street}</p>
          <p>{user.postalNumber} {user.city}</p>
          <p>{user.country}</p>
          <button className="auth-btn" style={{ marginTop: '20px', background: '#ff4444' }} onClick={onLogout}>
            WYLOGUJ SIĘ
          </button>
        </div>

        {/* Sekcja Zamówień */}
        <div className="profile-card" style={{ border: '1px solid #ddd', padding: '20px', borderRadius: '8px' }}>
          <h3>Status konta</h3>
          <p>Użytkownik aktywny</p>
          <div style={{ marginTop: '20px', padding: '10px', background: '#f9f9f9' }}>
            <p><strong>Dostępne rabaty:</strong> 0%</p>
            <p><strong>Historia:</strong> Brak ostatnich zamówień.</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;