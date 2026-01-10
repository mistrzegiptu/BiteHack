import React from 'react';
import './Header.css';
import { FiSearch, FiUser, FiHeart, FiShoppingBag } from 'react-icons/fi';
import { Link } from 'react-router-dom';

const Header = () => {
  const menuItems = [
    { id: 1, label: 'Sklep', path: '/shop', isSpecial: true },
    { id: 2, label: 'Rezerwacje', path: '/kategoria/title-2' },
    { id: 3, label: 'O nas', path: '/kategoria/title-3' },
  ];

  return (
    <header className="navbar">
      <div className="navbar-left">
        {/* Kliknięcie w LOGO wraca na stronę główną ("/") */}
        <Link to="/" className="logo" style={{ textDecoration: 'none', color: 'black' }}>
          LOGO_FIRMY
        </Link>
        
        <nav>
          <ul className="nav-links">
            {menuItems.map((item) => (
              <li key={item.id}>
                {/* ZAMIENIAMY <a> na <Link> */}
                <Link 
                  to={item.path} 
                  className={item.isSpecial ? 'link-special' : 'link-regular'}
                >
                  {item.label}
                </Link>
              </li>
            ))}
          </ul>
        </nav>
      </div>

      <div className="navbar-right">
        <div className="search-bar">
          <input type="text" placeholder="Szukaj..." />
          <FiSearch className="search-icon" />
        </div>

        <div className="user-actions">
          <FiUser className="icon" />
          <FiHeart className="icon" />
          <div className="icon-wrapper">
             {/* Ikona koszyka też może być linkiem */}
            <Link to="/koszyk">
                <FiShoppingBag className="icon" />
            </Link>
            <span className="badge">0</span>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;