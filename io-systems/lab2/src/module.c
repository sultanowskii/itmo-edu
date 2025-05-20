#include <linux/module.h>
#include <linux/cdev.h>
#include <linux/fs.h>
#include <linux/kdev_t.h>

#define MODULE_NAME "lab2"
#define DEVICE_NAME "var2"

static dev_t device; 
static struct cdev char_device;
static struct class *cls;

static size_t space_counts[1000];
static size_t space_counts_next_index = 0;

static int lab2_open(struct inode *inode, struct file *file) { 
    return 0; 
} 

static int lab2_release(struct inode *inode, struct file *file) {
    return 0; 
}

static ssize_t lab2_read(
    struct file *filp,
    char __user *buf,
    size_t length,
    loff_t *offset
) { 
    char digits[32];

    size_t current_offset = 0;
    size_t digits_index = 0;
    size_t buf_index = 0;
    size_t space_count_index = 0;

    if (offset == NULL) {
        return 0;
    }

    while (space_count_index < space_counts_next_index && buf_index < length) {
        sprintf(digits, "%zu", space_counts[space_count_index]);

        digits_index = 0;
        while (buf_index < length && digits[digits_index] != '\0') {
            current_offset++;
            if (current_offset > *offset) {
                put_user(digits[digits_index], buf + buf_index);
                buf_index++;
            }
            digits_index++;
        }

        if (buf_index < length) {
            current_offset++;
            if (current_offset > *offset) {
                put_user('\n', buf + buf_index);
                buf_index++;
            }
        }

        space_count_index++;
    }

    *offset += buf_index;

    return buf_index; 
} 

static ssize_t lab2_write(
    struct file *filp,
    const char __user *buf,
    size_t length,
    loff_t *offset
) {
    size_t cntr = 0;
    const char __user *bufptr = buf;

    size_t bytes_written = 0;
    while (bytes_written < length) {
        char c = '\0';
        get_user(c, bufptr);
        cntr += c == ' ';

        bytes_written++;
        bufptr++;
    }

    space_counts[space_counts_next_index] = cntr;
    space_counts_next_index++;

    return bytes_written;
}

static struct file_operations chardev_fops = { 
    .read = lab2_read, 
    .write = lab2_write, 
    .open = lab2_open, 
    .release = lab2_release, 
}; 

static int __init lab2_init(void) {
    int ret = 0;

    space_counts_next_index = 0;

    if (alloc_chrdev_region(&device, 0, 1, MODULE_NAME) < 0) {
		pr_err("alloc_chrdev_region() error");
        ret = -1;
        goto RET_OK;
	}
    if ((cls = class_create(THIS_MODULE, DEVICE_NAME)) == NULL) {
		pr_err("class_create() error");
		ret = -1;
        goto RET_CHRDEV_ALLOCED;
	}
    if (device_create(cls, NULL, device, NULL, DEVICE_NAME) == NULL) {
		pr_err("device_create() error");
        ret = -1;
        goto RET_CLASS_CREATED;
    }

    cdev_init(&char_device, &chardev_fops);

    if (cdev_add(&char_device, device, 1) == -1) {
		ret = -1;
        goto RET_DEVICE_CREATED;
    }
    goto RET_OK;

RET_DEVICE_CREATED:
    device_destroy(cls, device);
RET_CLASS_CREATED:
    class_destroy(cls);
RET_CHRDEV_ALLOCED:
    unregister_chrdev_region(device, 1);
RET_OK:
    return ret;
}

static void __exit lab2_exit(void) {
    cdev_del(&char_device);
    device_destroy(cls, device);
    class_destroy(cls);
    unregister_chrdev_region(device, 1);
}

module_init(lab2_init);
module_exit(lab2_exit);

MODULE_AUTHOR("Artur Sultanov");
MODULE_LICENSE("GPL");
