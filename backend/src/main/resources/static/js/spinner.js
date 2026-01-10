/**
 * SpinnerHelper - Reusable component for managing Bootstrap spinners during async operations
 * 
 * This component provides a consistent way to show loading spinners on buttons during
 * REST API calls, improving user experience by providing visual feedback.
 * 
 * Features:
 * - Automatically shows/hides Bootstrap spinner on button click
 * - Disables button during async operation to prevent duplicate submissions
 * - Re-enables button when operation completes (success or error)
 * - Handles both success and error scenarios
 * - Works with any button that contains a spinner element
 * 
 * Usage:
 * SpinnerHelper.executeWithSpinner(
 *     $('#myButton'),           // jQuery button element
 *     function() {              // Promise-returning function (e.g., $.ajax)
 *         return $.ajax({...});
 *     },
 *     function(data) {          // Success callback
 *         console.log('Success', data);
 *     },
 *     function(error) {         // Error callback
 *         console.error('Error', error);
 *     }
 * );
 */
const SpinnerHelper = (function() {
    'use strict';

    /**
     * Shows the spinner and disables the button
     * @param {jQuery} $button - The button element
     */
    function showSpinner($button) {
        $button.prop('disabled', true);
        $button.find('.btn-text').addClass('d-none');
        $button.find('.spinner-border').removeClass('d-none');
    }

    /**
     * Hides the spinner and re-enables the button
     * @param {jQuery} $button - The button element
     */
    function hideSpinner($button) {
        $button.prop('disabled', false);
        $button.find('.btn-text').removeClass('d-none');
        $button.find('.spinner-border').addClass('d-none');
    }

    /**
     * Executes an async operation with spinner management
     * @param {jQuery} $button - The button element to attach spinner to
     * @param {Function} asyncFunction - Function that returns a Promise (e.g., $.ajax call)
     * @param {Function} onSuccess - Success callback function
     * @param {Function} onError - Error callback function
     */
    function executeWithSpinner($button, asyncFunction, onSuccess, onError) {
        // Show spinner and disable button
        showSpinner($button);

        // Execute the async operation
        asyncFunction()
            .done(function(data) {
                // Hide spinner on success
                hideSpinner($button);
                if (onSuccess) {
                    onSuccess(data);
                }
            })
            .fail(function(error) {
                // Hide spinner on error
                hideSpinner($button);
                if (onError) {
                    onError(error);
                }
            });
    }

    /**
     * Alternative method using Promises (for native fetch API or other Promise-based APIs)
     * @param {jQuery} $button - The button element to attach spinner to
     * @param {Function} asyncFunction - Function that returns a Promise
     * @param {Function} onSuccess - Success callback function
     * @param {Function} onError - Error callback function
     */
    function executeWithSpinnerPromise($button, asyncFunction, onSuccess, onError) {
        // Show spinner and disable button
        showSpinner($button);

        // Execute the async operation
        asyncFunction()
            .then(function(data) {
                // Hide spinner on success
                hideSpinner($button);
                if (onSuccess) {
                    onSuccess(data);
                }
            })
            .catch(function(error) {
                // Hide spinner on error
                hideSpinner($button);
                if (onError) {
                    onError(error);
                }
            });
    }

    // Public API
    return {
        executeWithSpinner: executeWithSpinner,
        executeWithSpinnerPromise: executeWithSpinnerPromise,
        showSpinner: showSpinner,
        hideSpinner: hideSpinner
    };
})();

// Make it available globally
window.SpinnerHelper = SpinnerHelper;
