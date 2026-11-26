export function getErrorMessage(error) {
  if (error?.response?.data?.message) return error.response.data.message;
  if (error?.response?.data?.error) return error.response.data.error;
  if (error?.code === 'ECONNABORTED') return 'The server took too long to respond. Please try again.';
  if (error?.request) return 'Could not reach the backend. Check that the Spring Boot server is running.';
  return error?.message || 'Something went wrong. Please try again.';
}
