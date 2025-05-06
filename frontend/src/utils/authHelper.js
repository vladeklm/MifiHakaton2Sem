export const getUserFromToken = () => {
    const token = localStorage.getItem('authToken');
    if (!token) return null;
  
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload;
    } catch (err) {
      console.error('Ошибка при расшифровке токена:', err);
      return null;
    }
  };
  
  export const getUserId = () => {
    const user = getUserFromToken();
    return user?.id_user || null;
  };