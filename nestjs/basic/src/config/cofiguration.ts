import 'dotenv/config';

export default () => ({
  database: {
    host: process.env.DB_HOST || '',
    user: process.env.DB_USER || '',
    name: process.env.DB_NAME || '',
    password: process.env.DB_PASSWORD || '',
    port: process.env.DATABASE_PORT || '',
  },
});
