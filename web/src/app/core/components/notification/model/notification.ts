export interface Notification {
  title: string;
  message: string;
  type: 'bg-success text-white' | 'bg-danger text-white' | 'bg-info';
};
